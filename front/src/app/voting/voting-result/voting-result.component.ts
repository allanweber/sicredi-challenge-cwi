import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FeedbackMessageService } from 'src/app/shared/services/feedback-message.service';
import { VotingResult } from '../model/voting-result.model';
import { VotingService } from '../services/voting.service';

@Component({
  selector: 'app-voting-result',
  templateUrl: './voting-result.component.html',
  styleUrls: ['./voting-result.component.css'],
})
export class VotingResultComponent implements OnInit, OnDestroy {
  private serviceSubscription: Subscription;
  private routeSubscription: Subscription;

  public result: VotingResult;

  constructor(
    private service: VotingService,
    private activatedRoute: ActivatedRoute,
    public messageService: FeedbackMessageService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.routeSubscription = this.activatedRoute.params.subscribe(params => {
      if (params.id) {
        this.serviceSubscription = this.service.getResult(params.id).subscribe(
          response => (this.result = response),
          error => {
            this.messageService.showErroMessage(error.error.message);
            this.router.navigate(['/']);
          },
        );
      } else {
        this.messageService.showWarningMessage('Votação não encontrada.');
      }
    });
  }

  ngOnDestroy(): void {
    if (this.serviceSubscription) {
      this.serviceSubscription.unsubscribe();
    }
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }
}
