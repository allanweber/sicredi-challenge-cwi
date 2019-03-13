import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FeedbackMessageService } from 'src/app/shared/services/feedback-message.service';
import { Answer } from '../model/answer.model';
import { VotingResponse } from '../model/voting-response.model';
import { VotingService } from '../services/voting.service';

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css'],
})
export class VoteComponent implements OnInit, OnDestroy {
  private serviceSubscription: Subscription;
  private routeSubscription: Subscription;
  private rulingId: string;

  public voting: VotingResponse;

  public voteForm = this.formBuilder.group({
    cpf: [
      null,
      Validators.compose([
        Validators.required,
        Validators.minLength(11),
        Validators.maxLength(11),
        Validators.pattern('^[0-9]*$'),
      ]),
    ],
  });

  constructor(
    private service: VotingService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    public messageService: FeedbackMessageService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.routeSubscription = this.activatedRoute.params.subscribe(params => {
      if (params.id) {
        this.rulingId = params.id;
        this.serviceSubscription = this.service
          .getVotingById(this.rulingId)
          .subscribe(
            response => {
              this.voting = response;
            },
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

  voteYes() {
    this.vote(Answer.YES);
  }

  voteNo() {
    this.vote(Answer.NO);
  }

  vote(answer: Answer) {
    this.serviceSubscription = this.service
      .vote(this.rulingId, this.voteForm.get('cpf').value, answer)
      .subscribe(
        response => {
          this.messageService.showSuccessMessage('Obrigado pelo seu voto.');
          this.router.navigate(['/']);
        },
        error => {
          this.messageService.showErroMessage(error.error.message);
        },
      );
  }

  ngOnDestroy(): void {
    if (this.serviceSubscription) {
      this.serviceSubscription.unsubscribe();
    }
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }

  showError(controlName: string) {
    return (
      this.voteForm.controls[controlName].dirty &&
      (this.voteForm.controls[controlName].hasError('required') ||
        this.voteForm.controls[controlName].hasError('minlength') ||
        this.voteForm.controls[controlName].hasError('pattern') ||
        this.voteForm.controls[controlName].hasError('maxlength'))
    );
  }
}
