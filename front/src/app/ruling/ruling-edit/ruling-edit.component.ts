import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { RulingService } from 'src/app/shared/services/ruling.service';
import { FeedbackMessageService } from 'src/app/shared/services/feedback-message.service';
import { Router } from '@angular/router';
import { Ruling } from 'src/app/shared/model/ruling.model';

@Component({
  selector: 'app-ruling-edit',
  templateUrl: './ruling-edit.component.html',
  styleUrls: ['./ruling-edit.component.css'],
})
export class RulingEditComponent implements OnInit, OnDestroy {
  private serviceSubscription: Subscription;

  public rulingForm = this.formBuilder.group({
    name: [null, Validators.required],
  });

  constructor(
    private service: RulingService,
    private formBuilder: FormBuilder,
    public messageService: FeedbackMessageService,
    private router: Router,
  ) {}

  ngOnInit() {}

  onSubmit() {
    this.serviceSubscription = this.service
      .saveRuling(this.rulingForm.get('name').value)
      .subscribe(
        () => {
          this.messageService.showSuccessMessage('Pauta criada com sucesso.');
          this.router.navigate(['/ruling']);
        },
        error => {
          this.messageService.showErroMessage(error.message);
        },
      );
  }

  ngOnDestroy(): void {
    if (this.serviceSubscription) {
      this.serviceSubscription.unsubscribe();
    }
  }

  showError(controlName: string) {
    return (
      this.rulingForm.controls[controlName].dirty &&
      this.rulingForm.controls[controlName].hasError('required')
    );
  }
}
