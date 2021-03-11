import {NgModule} from "@angular/core";
import {MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";

const modules = [
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatTableModule
];

@NgModule({
  imports: [modules],
  exports: [modules],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
  ]
})
export class MaterialModule {}
