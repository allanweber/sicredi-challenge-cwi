import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { VotingListComponent } from './voting-list/voting-list.component';
import { VotingEditComponent } from './voting-edit/voting-edit.component';
import { VotingResultComponent } from './voting-result/voting-result.component';
import { VoteComponent } from './vote/vote.component';
import { VotingHomeComponent } from './voting-home/voting-home.component';

const routes: Routes = [
  { path: 'home', component: VotingHomeComponent },
  { path: 'voting', component: VotingListComponent },
  { path: 'voting/create', component: VotingEditComponent },
  { path: 'voting/vote/:id', component: VoteComponent },
  { path: 'voting/result/:id', component: VotingResultComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class VotingRoutingModule {}
