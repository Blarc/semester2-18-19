function [izhod, crc] = naloga3(vhod, n, k)
  % Izvedemo dekodiranje binarnega niza vhod, ki je bilo
  % zakodirano s Hammingovim kodom H(n,k)
  % in poslano po zasumljenem kanalu.
  % Nad vhodom izracunamo vrednost crc po standardu CRC-16.
  %
  % vhod  - binarni vektor y (vrstica tipa double)
  % n     - stevilo bitov v kodni zamenjavi
  % k     - stevilo podatkovnih bitov v kodni zamenjavi
  % crc   - crc vrednost izracunana po CRC-16 
  %         nad vhodnim vektorjem (sestnajstisko)
  % izhod - vektor podatkovnih bitov, dekodiranih iz vhoda
  
  
  vhodna = vhod;
  
  m = n - k;
  
  H = narediH(n, m);
  
  vis = size(vhod)(2) / n;
  vhod = reshape(vhod, n, vis)';
  
  S = [];
  ind = [];
  dolz = size(vhod)(2);
  totalSize = vis * k;
  
  print = 1;
  
  for i = 1:vis
    ind = [];
    prazn = 0;
    S = (vhod(i, :) * H');
    
    S = mod(S,2);

    if(ajPrazn(S) != 0)
      ind = najdi(H,S,i);
      vhod(i,ind(1)); 
      vhod = flippityFloppityFloop(vhod,ind,i);
      vhod(i,ind(1));
    endif
    
  endfor
  
  
  izhod = vhod(:,1:k);
  izhod = reshape(izhod',totalSize,1)';
  register = naredCRC(vhodna);
  register = num2str(register);
  register = bin2dec(register);
  register = dec2hex(register);
  crc = register;

endfunction


function [register] = naredCRC(vhodna)
  
  dolzina = size(vhodna)(2);
  register = zeros(1,16);
  vektor = [0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0];
  
  for i = 1:dolzina
    
    if(xor(register(1,16),vhodna(i)) == 0)
      
      register = [0,register(1,1:15)];
      
    else
      
      register = xor(register,vektor);
      register = [1,register(1,1:15)];
      
    endif
    
  endfor
  
  register=register';
  register = flipud(register);
  register=register'
  
endfunction



function [vsota] = ajPrazn(matrika)
  vsota=0;
  for i = 1:size(matrika)(2)
    vsota = vsota + matrika(1,i);
  endfor

endfunction

function [matrika] = flippityFloppityFloop(vhod, indeks, vrstica)
  
  dolzina = size(indeks)(2);
  
  if(dolzina > 0)
  
    ind = indeks(1);
    vhod(vrstica,ind) = vhod(vrstica,ind) + 1;
    vhod(vrstica,ind) = mod(vhod(vrstica,ind), 2);
  
  endif
  
  
  matrika = vhod;
endfunction


function [indeks] = najdi(matrika, sindrom, vrstica)
  
  dolzina = size(matrika)(2);
  indeks = 0;
  
  for i = 1:dolzina
    stolpec = matrika(:,i);
    
    if (stolpec == sindrom')
      indeks = i;
      
    endif
    
  endfor
  
endfunction


function [matrika] = narediH(dolzina, visina)
  
  potenca = 1;
  m1 = [];
  m2 = [];

  for i = 1:dolzina
    
    if(i == potenca)
      stevilo = (dec2bin(potenca)) - 48;
      d1 = size(stevilo)(2);
      razlikaDolzin = visina - d1;
      stevilo = [zeros(1,razlikaDolzin), stevilo]';
      stevilo = flipud(stevilo);
      potenca = potenca * 2;
      m1 = [m1, stevilo];
      
    else
      stevilo = (dec2bin(i)) - 48;
      d2 = size(stevilo)(2);
      razlikaDolzin = visina - d2;
      stevilo = [zeros(1,razlikaDolzin), stevilo]';
      stevilo = flipud(stevilo);
      m2 = [m2, stevilo];
      
    endif
    
    
  endfor
  
  matrika = [m2, m1];
  
endfunction
