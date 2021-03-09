import {ResponseApiEntity} from "./response-api.entity";
import {Mapper} from "../../../core/base/mapper";

export class ResponseApiWrapperRepository<T> extends Mapper<ResponseApiEntity< T >, T> {

  mapFrom(param: ResponseApiEntity<T>): T {
    return param.result;
  }

  mapTo(param: T): ResponseApiEntity<T> {
    return {
      result: param,
      errors: []
    };
  }


}
