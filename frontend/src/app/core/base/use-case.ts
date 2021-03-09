import {Observable} from "rxjs";

export interface UseCase<S, T> {
  execute(param: S): Observable<T>;
}
