import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RulingListComponent } from './ruling-list/ruling-list.component';
import { RulingRoutingModule } from './ruling.routing';
import { RulingEditComponent } from './ruling-edit/ruling-edit.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [RulingListComponent, RulingEditComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RulingRoutingModule
  ]
})
export class RulingModule { }
