import {Injectable} from "@angular/core";
import {UseCase} from "../base/use-case";
import {Person} from "../domain/person.model";
import {Observable} from "rxjs";
import {PersonRepository} from "../repositories/person.repository";

@Injectable({
  providedIn: 'root'
})
export class GetPersonByIdUsecase implements UseCase<string, Person> {

  constructor(private personRepository: PersonRepository) {
  }

  execute(params: string): Observable<Person> {
    return this.personRepository.getPersonById(params);
  }

}
