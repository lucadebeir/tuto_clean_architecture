import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PersonListComponent} from "./person-list/person-list.component";
import {DialogComponent} from "./component/dialog/dialog.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "../../configurations/material.module";

@NgModule({
  declarations: [
    PersonListComponent,
    DialogComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
  ],
  exports: [
    PersonListComponent
  ],
  entryComponents: [
    DialogComponent
  ]
})
export class PersonModule { }
