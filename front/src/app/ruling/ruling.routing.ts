import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { RulingListComponent } from './ruling-list/ruling-list.component';
import { RulingEditComponent } from './ruling-edit/ruling-edit.component';

const routes: Routes = [
  { path: 'ruling', component: RulingListComponent },
  { path: 'ruling/create', component: RulingEditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RulingRoutingModule {}
