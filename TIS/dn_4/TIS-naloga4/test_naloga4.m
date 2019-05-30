% Funkcija za preverjanje naloge 4.
% Primer zagona:
% test_naloga4('primeri',1);

function success = test_naloga4(caseDir,caseID)
			
	% Nalozi vhodne podatke in resitev
	caseData = load([caseDir,filesep,num2str(caseID),'.mat']);
	
	% Pozeni 
	tic();
	[izhod] = naloga4(caseData.vhod, caseData.Fs);
	timeElapsed = toc();
	
	% Preveri rezultate
	printf('Rezultat za primer %d\n',caseID);
	printf('-----------------------------\n');
	success = strcmpi(izhod, caseData.izhod) || ( isempty(izhod) && isempty(caseData.izhod));
	printf('Uspeh: %d\n',success);	
	printf('Cas izvajanja: %f sekund.\n',timeElapsed);
  	printf('-----------------------------\n');

end
