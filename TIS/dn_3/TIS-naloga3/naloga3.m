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
  
  m = n - k;
  H = zeros(m, n);
  
  Y = reshape(vhod, n, columns(vhod) / n)';
  
  potencaDve = 1; 
  indexData = 1;
  indexSec = k + 1;
  for i = (1 : n)
    if (i == potencaDve)
      H(1:m, indexSec) = dec2bin(i, m) - '0';
      potencaDve *= 2;
      indexSec += 1;
    else
      H(1:m, indexData) = dec2bin(i, m) -'0';
      indexData += 1;
    endif
  endfor
  
  H = flipud(H);
  
  for i = 1:columns(vhod) / n
    tmp = Y(i, :) * H';
    tmp = mod(tmp, 2);
    
    if (sum(tmp) != 0)
      [z, index, zz] = intersect(H', tmp, 'rows');
      if (index > 0)
        Y(i, index) = mod(Y(i, index) + 1, 2);
      endif
    endif
      
  endfor
  
  izhod = Y(:, 1:k);
  izhod = reshape(izhod', columns(vhod) / n * k, 1)';
  
  vektor = [0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0];
  crc = zeros(1, 16);
  
  for i = 1:columns(vhod)
    if (xor(crc(1,16), vhod(i)) == 0)
      crc = [0, crc(1, 1:15)];
    else
      crc = xor(crc, vektor);
      crc = [1, crc(1, 1:15)];
    endif
  endfor
  
  crc = dec2hex(bin2dec(num2str(flipud(crc')')));
  
end