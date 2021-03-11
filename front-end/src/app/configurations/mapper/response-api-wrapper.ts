import {Mapper} from "./mapper";
import {ResponseApi} from "../../models/response-api.model";

export class ResponseApiWrapper<T> extends Mapper<ResponseApi< T >, T> {

  mapFrom(param: ResponseApi<T>): T {
    return param.result;
  }

  mapTo(param: T): ResponseApi<T> {
    return {
      result: param,
      errors: []
    };
  }
}
