import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Ruling } from '../model/ruling.model';

@Injectable({
  providedIn: 'root',
})
export class RulingService {
  private route = `${environment.api}ruling`;

  constructor(private httpClient: HttpClient) {}

  getRuling(): Observable<Ruling[]> {
    return this.httpClient.get<Ruling[]>(this.route);
  }

  saveRuling(name: string): Observable<Ruling[]> {
    return this.httpClient.post<Ruling[]>(this.route, { name });
  }
}
