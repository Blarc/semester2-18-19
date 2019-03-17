% Funkcija za preverjanje naloge 1.
% Primer zagona:
% test_naloga1('primeri',1);

function success = test_naloga1(caseDir,caseID)
	
  	% Zahtevana natancnost rezultata
	tol = 1e-3;
  
	% Nalozi vhodne podatke in resitev
	caseData = load([caseDir,filesep,num2str(caseID),'.mat']);
	
	
	% Pozeni
	clear naloga1; % Za vsak primer pocistimo ime funkcije.
	tic();
	[H, R] = naloga1(caseData.besedilo,caseData.p);
	timeElapsed = toc();
	
  	% Preveri rezultate
	success = (abs(H - caseData.H) < tol) & (abs(R - caseData.R) < tol);
	printf('Vas rezultat: H = %.5f, R = %.5f\n', H, R);
	printf('Resitev:      H = %.5f, R = %.5f\n', caseData.H, caseData.R);
	printf('St. tock za primer %d: %d\n',caseID,success);
  	printf('Cas izvajanja: %f sekund\n',timeElapsed);

end
