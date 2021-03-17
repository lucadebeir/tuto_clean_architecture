import { Injectable } from '@angular/core';
import {OnlineOfflineService} from "../online-offline/online-offline.service";
import {HttpClient} from "@angular/common/http";
import * as localforage from "localforage";
import {Observable} from "rxjs";
import {Team} from "../../models/team.model";
import {ResponseApiWrapper} from "../../configurations/mapper/response-api-wrapper";
import {map} from "rxjs/operators";
import {ResponseApi} from "../../models/response-api.model";
import {Person} from "../../models/person.model";

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  mapper = new ResponseApiWrapper<any>();

  constructor(private readonly onlineOfflineService: OnlineOfflineService, private http: HttpClient) {
    localforage.config({
      driver: localforage.INDEXEDDB
    });

    this.registerToEvents(onlineOfflineService);
  }

  //find all team online
  public getAllTeams(): Observable<Team[]> {
    return this.http
      .get("http://localhost:8080/api/team/all")
      .pipe(map( (value: ResponseApi<Team[]>) => {
        localforage.setItem('teams', value.result);
        return this.mapper.mapFrom(value);
      }));
  }

  // OFFLINE/ONLINE
  private registerToEvents(onlineOfflineService: OnlineOfflineService) {
    onlineOfflineService.connectionChanged.subscribe(online => {
      if (online) {
        console.log('went online');
        console.log('sending all stored items');
      } else {
        console.log('went offline, storing in localforage');
      }
    });
  }

  create(data: Person) {
    
  }
}
