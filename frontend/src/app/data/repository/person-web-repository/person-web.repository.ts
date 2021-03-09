import {Injectable} from "@angular/core";
import {PersonRepository} from "../../../core/repositories/person.repository";
import {Observable} from "rxjs";
import {Person} from "../../../core/domain/person.model";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {ResponseApiEntity} from "./response-api.entity";
import {ResponseApiWrapperRepository} from "./response-api-wrapper.repository";

@Injectable({
  providedIn: 'root'
})
export class PersonWebRepository extends PersonRepository {

  mapper = new ResponseApiWrapperRepository<any>();

  constructor(private http: HttpClient) {
    super();
  }

  getAllPersons(): Observable<Person[]> {
    return this.http
      .get<ResponseApiEntity<Person[]>>('http://localhost:8080/api/person/all')
      .pipe(map(this.mapper.mapFrom));
  }

  getPersonById(id: string): Observable<Person> {
    return this.http
      .get<ResponseApiEntity<Person>>('http://localhost:8080/api/person/' + id)
      .pipe(map((value: ResponseApiEntity<Person>) => {
        return this.mapper.mapFrom(value);
      }));
  }

  create(person: Person): Observable<Person> {
    return this.http
      .post<ResponseApiEntity<Person>>('http://localhost:8080/api/person/add', person)
      .pipe(map(this.mapper.mapFrom));
  }



}
