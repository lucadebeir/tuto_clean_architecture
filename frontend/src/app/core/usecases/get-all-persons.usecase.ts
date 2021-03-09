import {UseCase} from "../base/use-case";
import {Person} from "../domain/person.model";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {PersonRepository} from "../repositories/person.repository";

@Injectable({
  providedIn: 'root'
})
export class GetAllPersonsUsecase implements UseCase<void, Person[]> {

  constructor(private personRepository: PersonRepository) {
  }

  execute(param: void): Observable<Person[]> {
    return this.personRepository.getAllPersons();
  }

}
