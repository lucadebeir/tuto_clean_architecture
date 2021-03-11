import {Injectable} from "@angular/core";
import {PersonRepository} from "../../../core/repositories/person.repository";
import {from, fromEvent, Observable, of, Subscription} from "rxjs";
import {Person} from "../../../core/domain/person.model";
import {HttpClient} from "@angular/common/http";
import {map, shareReplay} from "rxjs/operators";
import {ResponseApiEntity} from "./response-api.entity";
import {ResponseApiWrapperRepository} from "./response-api-wrapper.repository";

const CACHE_SIZE = 1;

@Injectable({
  providedIn: 'root'
})
export class PersonWebRepository extends PersonRepository {

  mapper = new ResponseApiWrapperRepository<any>();
  onlineEvent: Observable<Event>;
  offlineEvent: Observable<Event>;
  subscriptions: Subscription[] = [];
  online: boolean = true;
  sendToServer: Array<Person> = new Array();

  private allPersons$?: Observable<Person[]>;

  constructor(private http: HttpClient) {
    super();

    this.onlineEvent = fromEvent(window, 'online');
    this.offlineEvent = fromEvent(window, 'offline');

    this.subscriptions.push(this.onlineEvent.subscribe(e => {
      console.log('Online...');
      this.online = true;
      localStorage.clear(),
      console.log(this.sendToServer)
      setTimeout(() =>
      this.sendToServer.forEach(p => {
        this.create(p);
      }), 10000);
    }));

    this.subscriptions.push(this.offlineEvent.subscribe(e => {
      console.log('Offline...');
      this.online = false;
    }));
  }

  //ALL PERSONS
  get persons() {
    const fromStorage = JSON.parse(<string>localStorage.getItem("allPersons"));
    if(fromStorage) {
      return of(fromStorage);
    } else if(!this.allPersons$) {
      this.allPersons$ = this.requestAllPersons().pipe(
        //shareReplay(CACHE_SIZE)
      );
    }
    return this.allPersons$;
  }

  private requestAllPersons() {
    return this.http
      .get<ResponseApiEntity<Person[]>>('http://localhost:8080/api/person/all')
      .pipe(map((value: ResponseApiEntity<Person[]>) => {
        localStorage.setItem("allPersons", JSON.stringify(this.mapper.mapFrom(value)));
        return this.mapper.mapFrom(value);
      }));
  }

  getAllPersons(): Observable<Person[]> {
      return this.persons;
  }

  //ONE PERSON BY ID
  getPersonById(id: string): Observable<Person> {
    return this.http
      .get<ResponseApiEntity<Person>>('http://localhost:8080/api/person/' + id)
      .pipe(
        shareReplay(1),
        map((value: ResponseApiEntity<Person>) => {
        return this.mapper.mapFrom(value);
      }));
  }

  //CREATE A PERSON
  createPerson(person: Person) {
    if(this.online) {
      return this.requestCreatePerson(person);
    } else {
      console.log(this.sendToServer)
      this.sendToServer.push(person);
      console.log(this.sendToServer)
      const fromStorage = JSON.parse(<string>localStorage.getItem("allPersons"));
      if(fromStorage) {
        fromStorage.push({
          "lastName": person.firstName,
          "firstName": person.lastName,
          "age": new Number(person.age)
        });
        localStorage.setItem("allPersons", JSON.stringify(fromStorage));
        return of(fromStorage);
      } else {
        const toStorage = {
          "lastName": person.firstName,
          "firstName": person.lastName,
          "age": new Number(person.age)
        };
        localStorage.setItem("allPersons", JSON.stringify(toStorage));
        return of(toStorage);
      }
    }
  }

  requestCreatePerson(person: Person) {
    console.log(person)
    return this.http
      .post<ResponseApiEntity<Person>>('http://localhost:8080/api/person/add', person)
      .pipe(map(value => {
        console.log(value)
        return this.mapper.mapFrom(value);
      }));
  }

  create(person: Person): Observable<Person> {
    console.log(this.sendToServer)
    return this.createPerson(person);
  }



}
