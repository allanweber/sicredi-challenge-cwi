import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VotingResponse } from '../model/voting-response.model';
import { VotingRequest } from '../model/voting-request.model';
import { Answer } from '../model/answer.model';
import { VotingResult } from '../model/voting-result.model';

@Injectable({
  providedIn: 'root',
})
export class VotingService {
  private route = `${environment.api}voting`;

  constructor(private httpClient: HttpClient) {}

  getVoting(): Observable<VotingResponse[]> {
    return this.httpClient.get<VotingResponse[]>(this.route);
  }

  getVotingNotExpired(): Observable<VotingResponse[]> {
    return this.httpClient.get<VotingResponse[]>(`${this.route}/not-expired`);
  }

  getVotingById(votingId: string): Observable<VotingResponse> {
    return this.httpClient.get<VotingResponse>(`${this.route}/${votingId}`);
  }

  saveVoting(voting: VotingRequest): Observable<VotingResponse[]> {
    return this.httpClient.post<VotingResponse[]>(this.route, voting);
  }

  vote(rulingId: string, cpf: string, answer: Answer): Observable<any> {
    return this.httpClient.put(
      `${this.route}/vote/${rulingId}/${answer.toString()}`,
      null,
      { headers: { 'user-id': cpf } },
    );
  }

  getResult(rulingId: string): Observable<VotingResult> {
    return this.httpClient.get<VotingResult>(
      `${this.route}/result/${rulingId}`,
    );
  }
}
