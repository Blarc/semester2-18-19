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

  izhod = nan;
  crc = nan;
  
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
   
  H' * Y
  
  # error = mod((H'*Y), 2)
  # potencaDve = 1;
  # sum = 0;
  # for i = (1 : rows(error))
  #  if (error(i, 1) == 1)
  #    sum += potencaDve;
  #  endif
  #  potencaDve *= 2;
  #endfor
 
  
end