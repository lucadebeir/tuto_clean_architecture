import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PersonListComponent} from "./pages/person/person-list/person-list.component";
import {TeamListComponent} from "./pages/team/team-list/team-list.component";

const routes: Routes = [
  {
    path: "",
    component: PersonListComponent,
    data: { title: "Gestion des personnes"}
  },
  {
    path:"team",
    component: TeamListComponent,
    data: { title: "Gestion des équipes" }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
