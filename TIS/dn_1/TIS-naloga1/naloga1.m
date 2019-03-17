function [H,R] = naloga1(besedilo,p)
  % besedilo - stolpicni vektor znakov (char)
  % p  - stevilo poznanih predhodnih znakov; 0, 1, 2 ali 3.
  %    p = 0 pomeni, da racunamo povprecno informacijo na znak
  %        abecede brez poznanih predhodnih znakov: H(X1)
  %    p = 1 pomeni, da racunamo povprecno informacijo na znak 
  %        abecede pri enem poznanem predhodnemu znaku: H(X2|X1)
  %    p = 2: H(X3|X1,X2)
  %    p = 3: H(X4|X1,X2,X3)
  %
  % H - skalar; povprecna informacija na znak abecede 
  %     z upostevanjem stevila poznanih predhodnih znakov p
  % R - skalar; redundanca znaka abecede z upostevanjem 
  %     stevila poznanih predhodnih znakov p
  if (p == 0)
     [H, R] = prvi_priblizek(besedilo);
  endif
  
  if (p == 1)
     [H, R] = drugi_priblizek(besedilo);
  endif
  
  if (p == 2)
     [H, R] = tretji_priblizek(besedilo);
  endif
  
  if (p == 3)
     [H, R] = cetrti_priblizek(besedilo);
  endif

end

function [H,R] = prvi_priblizek(besedilo)
  % H(x1) = -sum(1,n)pi*log(pi)
  % pi - frekvenca crke
  
  % filtriramo besedilo
  besedilo = upper(besedilo(isalnum(besedilo)));
  st_crk = histc(besedilo, unique(besedilo));
  frekvence = st_crk / sum(st_crk);
  
  % izracunamo entropijo ter redundanco
  H = -frekvence'*log2(frekvence);
  R = 1 - H / log2(length(st_crk));
  
endfunction

function [H,R] = drugi_priblizek(besedilo)
  besedilo = upper(besedilo(isalnum(besedilo)));
  pari = [besedilo(1:end-1), besedilo(2:end)];
  [U, I, preslikava] = unique(pari, 'rows');
  st_parov = histc(preslikava, unique(preslikava));
  frekvence = st_parov / sum(st_parov);
  
  H_prvi = prvi_priblizek(besedilo);
  
  H = -frekvence'*log2(frekvence) - H_prvi;
  R = 1 - H / log2(length((unique(pari))));
endfunction

function [H,R] = tretji_priblizek(besedilo)
  besedilo = upper(besedilo(isalnum(besedilo)));
  pari = [besedilo(1:end-2), besedilo(2:end-1), besedilo(3:end)];
  [U, I, preslikava] = unique(pari, 'rows');
  st_parov = histc(preslikava, unique(preslikava));
  frekvence = st_parov / sum(st_parov);
  
  H_prvi = prvi_priblizek(besedilo);
  H_drugi = drugi_priblizek(besedilo);
  
  H = -frekvence'*log2(frekvence) - H_drugi - H_prvi;
  R = 1 - H / log2(length(unique(pari)));
endfunction

function [H,R] = cetrti_priblizek(besedilo)
  besedilo = upper(besedilo(isalnum(besedilo)));
  pari = [besedilo(1:end-3), besedilo(2:end-2), besedilo(3:end-1), besedilo(4:end)];
  [U, I, preslikava] = unique(pari, 'rows');
  st_parov = histc(preslikava, unique(preslikava));
  frekvence = st_parov / sum(st_parov);
  
  H_prvi = prvi_priblizek(besedilo);
  H_drugi = drugi_priblizek(besedilo);
  H_tretji = tretji_priblizek(besedilo);
  
  H = -frekvence'*log2(frekvence) - H_tretji - H_drugi - H_prvi;
  R = 1 - H / log2(length(unique(pari)));
endfunction

