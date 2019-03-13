import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing';
import { MenuComponent } from './menu/menu.component';
import { RulingModule } from './ruling/ruling.module';
import { RulingService } from './shared/services/ruling.service';
import { FeedbackMessageService } from './shared/services/feedback-message.service';
import { VotingModule } from './voting/voting.module';

@NgModule({
  declarations: [AppComponent, MenuComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    RulingModule,
    VotingModule,
    AppRoutingModule,
  ],
  providers: [RulingService, FeedbackMessageService],
  bootstrap: [AppComponent],
})
export class AppModule {}
