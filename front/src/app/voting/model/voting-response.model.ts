import { Ruling } from 'src/app/shared/model/ruling.model';

export class VotingResponse {
  id: string;
  ruling: Ruling;
  expiration: number;
  expired: boolean;
  expirationDate: Date;
}
