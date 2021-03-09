import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PersonListComponent} from "./person/person-list/person-list.component";
import {CoreModule} from "../core/core.module";
import {DataModule} from "../data/data.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "../configurations/material.module";
import { DialogComponent } from './person/component/dialog/dialog.component';

@NgModule({
  declarations: [
    PersonListComponent,
    DialogComponent
  ],
  imports: [
    CommonModule,
    CoreModule,
    DataModule,
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
export class PresentationModule { }
