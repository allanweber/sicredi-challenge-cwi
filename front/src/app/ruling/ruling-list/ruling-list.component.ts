import { Component, OnInit, OnDestroy } from '@angular/core';
import { RulingService } from 'src/app/shared/services/ruling.service';
import { Ruling } from 'src/app/shared/model/ruling.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-ruling-list',
  templateUrl: './ruling-list.component.html',
  styleUrls: ['./ruling-list.component.css'],
})
export class RulingListComponent implements OnInit, OnDestroy {
  private serviceSubscription: Subscription;

  public ruling: Ruling[] = [];

  constructor(private service: RulingService) {}

  ngOnInit() {
    this.serviceSubscription = this.service
      .getRuling()
      .subscribe(response => (this.ruling = response));
  }

  ngOnDestroy(): void {
    if (this.serviceSubscription) {
      this.serviceSubscription.unsubscribe();
    }
  }
}
