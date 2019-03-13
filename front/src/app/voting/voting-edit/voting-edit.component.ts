import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';
import { FeedbackMessageService } from 'src/app/shared/services/feedback-message.service';
import { Router } from '@angular/router';
import { RulingService } from 'src/app/shared/services/ruling.service';
import { VotingService } from '../services/voting.service';
import { Ruling } from 'src/app/shared/model/ruling.model';
import { VotingRequest } from '../model/voting-request.model';

@Component({
  selector: 'app-voting-edit',
  templateUrl: './voting-edit.component.html',
  styleUrls: ['./voting-edit.component.css'],
})
export class VotingEditComponent implements OnInit, OnDestroy {
  private votingSubscription: Subscription;
  private rulingSubscription: Subscription;

  public ruling: Ruling[];

  public votingForm = this.formBuilder.group({
    expiration: [null, Validators.pattern('^[0-9]*$')],
    rulingId: [null, Validators.required],
  });

  constructor(
    private votingService: VotingService,
    private rulingService: RulingService,
    private formBuilder: FormBuilder,
    public messageService: FeedbackMessageService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.rulingSubscription = this.rulingService
      .getRuling()
      .subscribe(response => (this.ruling = response));
  }

  onSubmit() {
    const voting: VotingRequest = {
      expiration: this.votingForm.get('expiration').value,
      rulingId: this.votingForm.get('rulingId').value,
    };

    this.votingSubscription = this.votingService.saveVoting(voting).subscribe(
      () => {
        this.messageService.showSuccessMessage('Votação criada com sucesso.');
        this.router.navigate(['/voting']);
      },
      error => {
        this.messageService.showErroMessage(error.error.message);
      },
    );
  }

  ngOnDestroy(): void {
    if (this.votingSubscription) {
      this.votingSubscription.unsubscribe();
    }
    if (this.rulingSubscription) {
      this.rulingSubscription.unsubscribe();
    }
  }

  showError(controlName: string) {
    return (
      this.votingForm.controls[controlName].dirty &&
      (this.votingForm.controls[controlName].hasError('required') ||
        this.votingForm.controls[controlName].hasError('pattern'))
    );
  }
}
