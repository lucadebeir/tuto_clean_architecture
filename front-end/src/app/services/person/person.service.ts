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
  persons: Person[];

  constructor(private readonly onlineOfflineService: OnlineOfflineService, private http: HttpClient) {
    localforage.config({
      driver: localforage.INDEXEDDB
    });

    this.registerToEvents(onlineOfflineService);
  }

  getAllPersons(): Observable<Person[]> {
    return this.http
      .get<ResponseApi<Person[]>>('http://localhost:8080/api/person/all')
      .pipe(map((value: ResponseApi<Person[]>) => {
        this.addToLocalForage(this.mapper.mapFrom(value));
        return this.mapper.mapFrom(value);
      }));
  }

  getPersonByUuid(uuid: string): Observable<Person> {
    return this.http
      .get<ResponseApi<Person>>('http://localhost:8080/api/person/' + uuid)
      .pipe(map(this.mapper.mapFrom));
  }

  create(person: Person): Observable<Person> {
    person.uuid = uuidv4();
    console.log(person)
    return this.http
      .post<ResponseApi<Person>>('http://localhost:8080/api/person/add', person)
      .pipe(map(this.mapper.mapFrom));
  }

  // OFFLINE/ONLINE
  private registerToEvents(onlineOfflineService: OnlineOfflineService) {
    onlineOfflineService.connectionChanged.subscribe(online => {
      if (online) {
        console.log('went online');
        console.log('sending all stored items');

        this.sendItemsFromIndexedDb();
      } else {
        console.log('went offline, storing in indexdb');
      }
    });
  }

  async addToIndexedDb(person: Person) {
    person.uuid = uuidv4();
    try {
      this.persons = await localforage.getItem('persons');
      this.persons.push(person);
      console.log(this.persons);
      this.addToLocalForage(this.persons);
    } catch (err) {
      console.log(err);
    }
  }

  private addToLocalForage(persons: Person[]) {
    localforage.setItem('persons', persons).then(function (value) {
      console.log(value);
    }).catch(function (err) {
      console.log(err);
    })
  }

  private async sendItemsFromIndexedDb() {
    const allItems: Person[] = await localforage.getItem('persons');

    console.log(allItems);

    allItems.forEach((item: Person) => {
      this.create(item).subscribe();
    });
  }
}
