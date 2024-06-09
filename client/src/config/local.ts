import { environment } from 'src/environments/environment';
import { Configuration } from './model';


export const config: Configuration = {
  apiUrl: environment.apiUrl,
  authUrl: environment.authUrl,
  clientId: environment.clientId,
  clientSecret: environment.clientSecret,
  recaptchaKey: environment.recaptchaKey,
  carausel: [
    {
      title: 'Title',
      text: 'Text',
      imageUrl: ''
    },
    {
      title: 'Title',
      text: 'Text',
      imageUrl: ''
    },
    {
      title: 'Title',
      text: 'Text',
      imageUrl: ''
    }
  ],
  bannerUrl: ''
};
