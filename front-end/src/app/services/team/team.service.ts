import { Injectable } from '@angular/core';
import {OnlineOfflineService} from "../online-offline/online-offline.service";
import {HttpClient} from "@angular/common/http";
import * as localforage from "localforage";
import {Observable} from "rxjs";
import {Team} from "../../models/team.model";
import {ResponseApiWrapper} from "../../configurations/mapper/response-api-wrapper";
import {map} from "rxjs/operators";
import {ResponseApi} from "../../models/response-api.model";
import { v4 as uuidv4 } from 'uuid';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  mapper = new ResponseApiWrapper<any>();
  toDelete = new Array<string>();
  uuidCreate = new Array<string>();

  constructor(private readonly onlineOfflineService: OnlineOfflineService, private http: HttpClient) {
    localforage.config({
      driver: localforage.INDEXEDDB
    });

    this.registerToEvents(onlineOfflineService);
  }

  //find all team online
  public getAllTeams(): Observable<Team[]> {
    return this.http
      .get("http://172.16.34.227:8080/api/team/all")
      .pipe(map( (value: ResponseApi<Team[]>) => {
        localforage.setItem('teams', value.result);
        return this.mapper.mapFrom(value);
      }));
  }

  //find team by uuid online
  getTeamByUuid(uuid: string): Observable<Team> {
    return this.http
      .get<ResponseApi<Team>>('http://172.16.34.227:8080/api/team/' + uuid)
      .pipe(map(this.mapper.mapFrom));
  }

  //create a team online
  create(team: Team): Observable<Team> {
    team.uuid === undefined ? team.uuid = uuidv4() : null;
    return this.http
      .post<ResponseApi<Team>>('http://172.16.34.227:8080/api/team/add', team)
      .pipe(map(this.mapper.mapFrom));
  }

  //update a team online
  update(team: Team): Observable<Team> {
    return this.http
      .post<ResponseApi<Team>>('http://172.16.34.227:8080/api/team/update', team)
      .pipe(map(this.mapper.mapFrom));
  }

  //delete a team online
  delete(uuid: string): void {
    this.http.delete('http://172.16.34.227:8080/api/team/' + uuid).subscribe();
  }

  // OFFLINE/ONLINE
  private registerToEvents(onlineOfflineService: OnlineOfflineService) {
    onlineOfflineService.connectionChanged.subscribe(online => {
      if (online) {
        console.log('went online');
        console.log('sending all stored items');

        this.sendItemsFromLocalForage().then(() => {
          this.uuidCreate.splice(0),
            this.toDelete.splice(0)
        });
      } else {
        console.log('went offline, storing in localforage');
      }
    });
  }

  //find a person by uuid from local forage offline
  async getTeamByUuidFromLocalForage(uuid: string) {
    let team: Team = null;
    try {
      await localforage.getItem('teams').then((items: Team[]) => {
        items.forEach(t => {
          if(t.uuid == uuid) {
            team = t;
          }
        })
      });
      return team;
    } catch (err) {
      console.log(err);
    }
  }

  //add new team on local forage offline
  async addToLocalForage(team: Team) {
    team.uuid = uuidv4();
    this.uuidCreate.push(team.uuid);
    try {
      let allItems: Team[] = await localforage.getItem('teams');
      allItems.push(team)
      await localforage.setItem('teams', allItems);
    } catch (err) {
      console.log(err);
    }
  }

  //update a team from local forage offline
  async updateFromLocalForage(team: Team) {
    try {
      await localforage.getItem('teams').then(async (items: Team[]) => {
        items.forEach(t => {
          if(t.uuid == team.uuid) {
            t.name = team.name;
            t.list = team.list;
          }
        })
        await localforage.setItem('teams', items);
      });
    } catch (err) {
      console.log(err);
    }
  }

  //delete a team from local forage offline
  async deleteFromLocalForage(uuid: string) {
    this.toDelete.push(uuid);
    if(this.uuidCreate.filter(u => u === uuid).length != 0) {
      this.uuidCreate.splice(this.uuidCreate.indexOf(uuid), 1);
    }
    try {
      await localforage.getItem('teams').then((items: Team[]) => {
        const teams: Team[] = items.filter(item => item.uuid != uuid);
        localforage.removeItem('teams');
        localforage.setItem('teams', teams);
      });
    } catch (err) {
      console.log(err);
    }
  }

  //send all the modification in relation to the list of teams from offline to online
  private async sendItemsFromLocalForage() {
    const allItems: Team[] = await localforage.getItem('teams');

    //when it exists, and the 2 objects are different, we modify it
    //otherwise, we add it to the database (<=> error 404 send from to the server)
    allItems.forEach((item: Team) => {
      if(this.uuidCreate.filter(uuid => uuid === item.uuid).length != 0) {
        this.create(item).subscribe();
      } else {
        this.getTeamByUuid(item.uuid).subscribe((data: Team) => {
          if(JSON.stringify(item) !== JSON.stringify(data)) {
            this.update(item).subscribe();
          }
          },
          () => {
            this.create(item).subscribe();
          });
      }
    });

    this.toDelete.forEach((uuid: string) => {
      this.getTeamByUuid(uuid).subscribe(() => {
        this.delete(uuid);
      });
    });
  }




}
