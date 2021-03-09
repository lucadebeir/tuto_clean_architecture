import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CoreModule} from "./core/core.module";
import {PresentationModule} from "./presentation/presentation.module";
import {PersonRepository} from "./core/repositories/person.repository";
import {PersonWebRepository} from "./data/repository/person-web-repository/person-web.repository";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {HttpErrorInterceptor} from "./data/interceptors/httperror.interceptor";
import {HttpConfigInterceptor} from "./data/interceptors/httpconfig.interceptor";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from "./configurations/material.module";
import {AlertModule} from "./components/_alert";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    PresentationModule,
    BrowserAnimationsModule,
    AlertModule
  ],
  providers: [
    {provide: PersonRepository, useClass: PersonWebRepository},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    },
    { provide: HTTP_INTERCEPTORS, useClass: HttpConfigInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
