import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import {Person} from "../../models/person.model";
import {ResponseApi} from "../../models/response-api.model";
import {ResponseApiWrapper} from "../../configurations/mapper/response-api-wrapper";
import * as localforage from "localforage";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  mapper = new ResponseApiWrapper<any>();

  constructor(private http: HttpClient) {
    localforage.config({
      driver: localforage.INDEXEDDB
    });
  }

  getAllPersons(): Observable<Person[]> {
    return this.http
      .get<ResponseApi<Person[]>>('http://localhost:8080/api/person/all')
      .pipe(map((value: ResponseApi<Person[]>) => {
        localforage.setItem('persons', this.mapper.mapFrom(value)).then(function (value) {
          // Do other things once the value has been saved.
          console.log(value);
        }).catch(function (err) {
          // This code runs if there were any errors
          console.log(err);
        })
        return this.mapper.mapFrom(value)
      }));
  }

  getPersonById(id: string): Observable<Person> {
    return this.http
      .get<ResponseApi<Person>>('http://localhost:8080/api/person/' + id)
      .pipe(map((value: ResponseApi<Person>) => {
        return this.mapper.mapFrom(value);
      }));
  }

  create(person: Person): Observable<Person> {
    return this.http
      .post<ResponseApi<Person>>('http://localhost:8080/api/person/add', person)
      .pipe(map(this.mapper.mapFrom));
  }
}
