white = [];
black = [];
i = 1;

if (vhod(1) == 0)
  white(1) = 0;
endif

while (i <= length(vhod))
  atm = vhod(i);
  temp = 0;
  while (i <= length(vhod) && vhod(i) == atm)
    temp += 1;
    i += 1;
  endwhile
  if (atm == 1)
    white = [white, temp];
  else
    black = [black, temp];
  endif
endwhile


function [huff] = huffwoman(huff, n)
  col = find(huff(:, 3) == 0);
  
  [freqSorted, indices] = sort(huff(:, 2)(col));
  
  newRow = [0, freqSorted(1) + freqSorted(2), 0, col(indices(1)), col(indices(2))];
  huff(col(indices(1)), 3) += 1;
  huff(col(indices(2)), 3) += 1;
  huff = [huff; newRow];
  
  increase = nonzeros([huff(col(indices(1)), 4:5),huff(col(indices(2)), 4:5)]);
  if (any(increase))
    while (any(increase > n))
      increase = [increase;huff(increase(find(increase > n)),4);huff(increase(find(increase > n)),5)];
      increase(find(increase > n)) = [];
    endwhile
    huff(increase, 3) += 1;
  endif
  
endfunction


function [codes] = encode(lens)
  codes = [];
  maxLen = max(lens(:,2));
  prevLen = lens(2,1);
  val = 0;
  for i = (1:length(lens))
    atmLen = lens(i, 2);
    
    if (atmLen != prevLen)
      val *= 2;
      prevLen = atmLen;
    endif
    
    codes = [codes; dec2bin(val,maxLen) - 48];
    val += 1;
  endfor
endfunction


function [result] = huffman(arr)
  uniq = unique(arr);

  hist = histc(arr, uniq);

  quot = sum(hist);

  freq = hist / quot;
  
  huff = [uniq', freq', zeros(length(freq),1,"float"), zeros(length(freq),1,"float"), zeros(length(freq),1,"float")];
  
  while (huff(end, 2) != 1)
    huff = huffwoman(huff, length(uniq));
  endwhile
  
  codeLen = [huff(1:length(uniq), 1), huff(1:length(uniq), 3)];
  codeLen = sortrows(sortrows(codeLen, [1]),[2])
  codes = encode(codeLen)
  
  V = codeLen(:,1)';
  I = (1:length(codeLen))';
  mapped = interp1(V, I, arr);
  
endfunction

huffman(white);
huffman(black);

  
  
  
  

