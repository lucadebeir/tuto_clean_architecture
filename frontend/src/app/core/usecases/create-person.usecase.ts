import {Injectable} from "@angular/core";
import {UseCase} from "../base/use-case";
import {Person} from "../domain/person.model";
import {PersonRepository} from "../repositories/person.repository";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CreatePersonUsecase implements UseCase<Person, Person> {

  constructor(private personRepository: PersonRepository) {
  }

  execute(param: Person): Observable<Person> {
    return this.personRepository.create(param);
  }

}
