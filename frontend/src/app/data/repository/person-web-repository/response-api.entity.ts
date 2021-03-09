export interface ResponseApiEntity<T> {
  result: T;
  errors: Array<string>
}
