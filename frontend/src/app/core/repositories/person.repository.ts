import {Observable} from "rxjs";
import {Person} from "../domain/person.model";

export abstract class PersonRepository {
  abstract getPersonById(id: string): Observable<Person>
  abstract getAllPersons(): Observable<Person[]>
  abstract create(person: Person): Observable<Person>
}
