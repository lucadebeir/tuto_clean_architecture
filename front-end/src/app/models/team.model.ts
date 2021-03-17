import {Person} from "./person.model";

export interface Team {
  uuid?: string;
  name?: string;
  list?: Person[];
}
