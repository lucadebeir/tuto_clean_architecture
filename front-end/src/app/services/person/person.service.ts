import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {Person} from "../../models/person.model";
import {ResponseApi} from "../../models/response-api.model";
import {ResponseApiWrapper} from "../../configurations/mapper/response-api-wrapper";
import * as localforage from "localforage";
import { v4 as uuidv4 } from 'uuid';
import {OnlineOfflineService} from "../online-offline/online-offline.service";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  mapper = new ResponseApiWrapper<any>();
  toDelete = new Array<string>();
  uuidCreate = new Array<string>();

  constructor(private readonly onlineOfflineService: OnlineOfflineService, private http: HttpClient) {
    localforage.config({
      driver: localforage.INDEXEDDB
    });

    this.registerToEvents(onlineOfflineService);
  }

  //find all person online
  getAllPersons(): Observable<Person[]> {
    return this.http
      .get<ResponseApi<Person[]>>('http://172.16.34.227:8080/api/person/all')
      .pipe(map( (value: ResponseApi<Person[]>) => {
        localforage.setItem('persons', value.result);
        return this.mapper.mapFrom(value);
      }));
  }

  //find person by uuid online
  getPersonByUuid(uuid: string): Observable<Person> {
    return this.http
      .get<ResponseApi<Person>>('http://172.16.34.227:8080/api/person/' + uuid)
      .pipe(map(this.mapper.mapFrom));
  }

  //add a new person online
  create(person: Person): Observable<Person> {
    person.uuid === undefined ? person.uuid = uuidv4() : null;
    return this.http
      .post<ResponseApi<Person>>('http://172.16.34.227:8080/api/person/add', person)
      .pipe(map(this.mapper.mapFrom));
  }

  //update a person online
  update(person: Person): Observable<Person> {
    return this.http
      .post<ResponseApi<Person>>('http://172.16.34.227:8080/api/person/update', person)
      .pipe(map(this.mapper.mapFrom));
  }

  //delete a person online
  delete(uuid: string): void {
    this.http.delete('http://172.16.34.227:8080/api/person/' + uuid).subscribe();
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
  async getPersonByUuidFromLocalForage(uuid: string) {
    let person: Person = null;
    try {
      await localforage.getItem('persons').then((items: Person[]) => {
        items.forEach(p => {
          if(p.uuid == uuid) {
            person = p;
          }
        })
      });
      return person;
    } catch (err) {
      console.log(err);
    }
  }

  //add new person on local forage offline
  async addToLocalForage(person: Person) {
    person.uuid = uuidv4();
    this.uuidCreate.push(person.uuid);
    try {
      let allItems: Person[] = await localforage.getItem('persons');
      allItems.push(person)
      await localforage.setItem('persons', allItems);
    } catch (err) {
      console.log(err);
    }
  }

  //update a person from local forage offline
  async updateFromLocalForage(person: Person) {
    try {
      await localforage.getItem('persons').then(async (items: Person[]) => {
        items.forEach(p => {
          if(p.uuid == person.uuid) {
            p.firstName = person.firstName;
            p.lastName = person.lastName;
            p.age = person.age;
          }
        })
        await localforage.setItem('persons', items);
      });
    } catch (err) {
      console.log(err);
    }
  }

  //delete a person from local forage offline
  async deleteFromLocalForage(uuid: string) {
    this.toDelete.push(uuid);
    if(this.uuidCreate.filter(u => u === uuid).length != 0) {
      this.uuidCreate.splice(this.uuidCreate.indexOf(uuid), 1);
    }
    try {
      await localforage.getItem('persons').then((items: Person[]) => {
        const persons: Person[] = items.filter(item => item.uuid != uuid);
        localforage.removeItem('persons');
        localforage.setItem('persons', persons);
      });
    } catch (err) {
      console.log(err);
    }
  }

  //send all the modification in relation to the list of persons from offline to online
  private async sendItemsFromLocalForage() {
    const allItems: Person[] = await localforage.getItem('persons');

    //when it exists, and the 2 objects are different, we modify it
    //otherwise, we add it to the database (<=> error 404 send from to the server)
    allItems.forEach((item: Person) => {
      if(this.uuidCreate.filter(uuid => uuid === item.uuid).length != 0) {
        this.create(item).subscribe();
      } else {
        this.getPersonByUuid(item.uuid).subscribe((data: Person) => {
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
      this.getPersonByUuid(uuid).subscribe(() => {
        this.delete(uuid);
      });
    });
  }

}
