import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VotingEditComponent } from './voting-edit/voting-edit.component';
import { VotingListComponent } from './voting-list/voting-list.component';
import { ReactiveFormsModule } from '@angular/forms';
import { VotingRoutingModule } from './voting.routing';
import { VotingService } from './services/voting.service';
import { VotingResultComponent } from './voting-result/voting-result.component';
import { VoteComponent } from './vote/vote.component';
import { VotingHomeComponent } from './voting-home/voting-home.component';

@NgModule({
  declarations: [VotingEditComponent, VotingListComponent, VotingResultComponent, VoteComponent, VotingHomeComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    VotingRoutingModule
  ],
  providers: [VotingService]
})
export class VotingModule { }
