import { HttpInterceptorFn } from '@angular/common/http';

/**
 * Interceptador funcional que adiciona o prefixo "/api"
 * a todas as requisições HTTP relativas (que começam com "/").
 *
 * @author Matheus F. N. Pereira
 */
export const prefixoApiInterceptor: HttpInterceptorFn = (request, next) => {
  if (request.url.startsWith('/')) {
    const apiRequest = request.clone({
      url: `/api${request.url}`,
    });

    return next(apiRequest);
  }

  return next(request);
};
