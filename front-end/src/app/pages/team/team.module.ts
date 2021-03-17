import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "../../configurations/material.module";
import {TeamListComponent} from "./team-list/team-list.component";
import {DialogComponent} from "./components/dialog/dialog.component";
import {TeamDetailsComponent} from "./components/team-details/team-details.component";

@NgModule({
  declarations: [
    TeamListComponent,
    DialogComponent,
    TeamDetailsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
  ],
  exports: [
    TeamListComponent
  ],
  entryComponents: [
    DialogComponent,
    TeamDetailsComponent
  ]
})
export class TeamModule { }
