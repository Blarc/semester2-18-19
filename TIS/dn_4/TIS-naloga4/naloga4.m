function izhod = naloga4(vhod,Fs)
% Funkcija naloga4 skusa poiskati akord v zvocnem zapisu.
%
% vhod  - vhodni zvocni zapis (vrsticni vektor tipa double) 
% Fs    - frekvenca vzorcenja
% izhod - ime akorda, ki se skriva v zvocnem zapisu (niz);
%         ce frekvence v zvocnem zapisu ne ustrezajo nobenemu
%         od navedenih akordov, vrnemo prazen niz [].


tones = [261.63, 277.18, 293.66, 311.13, 329.63, 349.23, 369.99, 392, 415.30, 440, 466.16, 493.88, 523.25, 554.37, 587.33, 622.25, 659.25, 698.46, 739.99, 783.99, 830.61, 880, 932.33, 987.77];

freqs = (abs(fft(vhod)).^2)/columns(vhod);

f = [];

for i = 1 : 24
  tmp = round(tones(i) * columns(vhod) / Fs)+1;
  f(i) = max([freqs(tmp-3), freqs(tmp-2), freqs(tmp-1), freqs(tmp), freqs(tmp+1), freqs(tmp+2), freqs(tmp+3)]);
endfor

[s, i] = sort(f, "descend")

ind = sort([i(1), i(2), i(3)]);

key = ind(1) * 10000 + ind(2) * 100 + ind(3)

izhod="";

# C DUR
if (key == 10508)
  izhod="Cdur";
endif
# C MOL
if (key == 20408)
  izhod="Cmol";
endif
# D DUR
if (key == 30710)
  izhod="Ddur";
endif
# D MOL
if (key == 30610)
  izhod="Dmol";
endif
# E DUR
if (key == 50912)
  izhod="Edur";
endif
# E MOL
if (key == 50812)
  izhod="Emol";
endif
# F DUR
if (key == 61013)
  izhod="Fdur";
endif
# F MOL
if (key == 60913)
  izhod="Fmol";
endif
# G DUR
if (key == 81215)
  izhod="Gdur";
endif
# G MOL
if (key == 81115)
  izhod="Gmol";
endif
# A DUR
if (key == 101417)
  izhod="Adur";
endif
# A MOL 
if (key == 101317)
  izhod="Amol";
endif
# H DUR
if (key == 121619)
  izhod="Hdur";
endif
# H MOL
if (key == 121519)
  izhod="Hmol";
endif