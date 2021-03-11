export interface ResponseApi<T> {
  result: T;
  errors: Array<string>
}
