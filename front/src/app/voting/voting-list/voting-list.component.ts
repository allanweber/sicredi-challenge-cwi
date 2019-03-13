import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { VotingResponse } from '../model/voting-response.model';
import { VotingService } from '../services/voting.service';

@Component({
  selector: 'app-voting-list',
  templateUrl: './voting-list.component.html',
  styleUrls: ['./voting-list.component.css']
})
export class VotingListComponent implements OnInit, OnDestroy {

  private serviceSubscription: Subscription;

  public voting: VotingResponse[] = [];

  constructor(private service: VotingService) { }

  ngOnInit() {
    this.serviceSubscription = this.service
    .getVoting()
    .subscribe(response => (this.voting = response));
  }

  ngOnDestroy(): void {
    if (this.serviceSubscription) {
      this.serviceSubscription.unsubscribe();
    }
  }

}
