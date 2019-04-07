function [izhod, R, kodBela, kodCrna] = naloga2(vhod)
  % Izvedemo kodiranje vhodne binarne slike (matrike) vhod
  % po modificiranem standardu ITU-T T.4.
  % Slika vsebuje poljubno stevilo vrstic in 1728 stolpcev.
  %
  % vhod     - matrika, ki predstavlja sliko
  % izhod    - binarni vrsticni vektor
  % R        - kompresijsko razmerje
  % kodBela  - matrika dolzin zaporedij belih slikovnih tock
  %		         in dolzin kodnih zamenjav
  % kodCrna  - matrika dolzin zaporedij crnih slikovnih tock
  %		         in dolzin kodnih zamenjav
  
  white = [];
  black = [];
  rowsVector = [];

  [rowNum, colNum] = size(vhod);

  for i = (1:rowNum)
    row = vhod(i, :);
    
    indRow = find(diff(row));
    indRow = [0, indRow, colNum];
    indRow = nonzeros(indRow(2:end) - indRow(1:end-1))';
    
    if (row(1) == 0)
      indRow = [0, indRow];
    endif
    
    whiteRow = indRow(1:2:end);
    blackRow = indRow(2:2:end);
    
    rowsVector = [rowsVector, indRow];
    white = [white, whiteRow];
    black = [black, blackRow];
    
  endfor
  
  function [huff] = huffwoman(huff, n)
    col = huff(:, 3);
    
    #[freqSorted, indices] = sort(huff(:, 2)(col));
    #[freqSorted, indices] = sort(huff(col, 2));
    
    freq = huff(:, 2);
    
    valueOne = min(freq);
    indexOne = find(freq==valueOne)(1);
    huff(indexOne, 2) = 1;
    
    freq = huff(:, 2);
    
    valueTwo = min(freq);
    indexTwo = find(freq == valueTwo)(1);
    huff(indexTwo, 2) = 1;
    
    freq = huff(:, 2);
    
    newRow = [0, valueOne + valueTwo, 0, indexOne, indexTwo];
    
    huff(indexOne, 3) += 1;
    huff(indexTwo, 3) += 1;
    huff = [huff; newRow];
    
    
    increase = nonzeros([huff(indexOne, 4:5),huff(indexTwo, 4:5)]);
    if (any(increase))
      while (any(increase > n))
        temp = find(increase > n);
        increase = [increase;huff(increase(temp),4);huff(increase(temp),5)];
        increase(temp) = [];
      endwhile
      huff(increase, 3) += 1;
    endif
    
  endfunction
  
  
  function [codes] = encode(lens)
    codes = [];
    maxLen = max(lens(:,2));
    prevLen = lens(1,2);
    val = 0;
    for i = (1:length(lens))
      atmLen = lens(i, 2);
      
      if (atmLen != prevLen)
        tempLen = atmLen - prevLen;
        for g = (1:tempLen)
          val *= 2;
        endfor
        prevLen = atmLen;
      endif
      
      codes = [codes; dec2bin(val,maxLen) - 48];
      val += 1;
    endfor
  endfunction
  
  
  function [codes, map, codeLen] = huffman(arr)
    uniq = unique(arr);

    hist = histc(arr, uniq);

    quot = sum(hist);
    
    freq = hist / quot;
    
    huff = [uniq', freq', zeros(length(freq),1,"float"), zeros(length(freq),1,"float"), zeros(length(freq),1,"float")];
    
    # TOLE
    while (huff(end, 2) < 0.9)
      huff = huffwoman(huff, length(uniq));
    endwhile
    
    codeLen = [huff(1:length(uniq), 1), huff(1:length(uniq), 3)];
    codeLen = sortrows(codeLen, [2,1]);
    codes = [codeLen(:,2),encode(codeLen)];
    
    indices = codeLen(:, 1);
    map = zeros(colNum+1, max(codeLen(:, 2))+1);
    map(indices+1,:) = codes;
    
    
  endfunction
  
  bs = 0;
  
  if (length(white) > 1 && length(black) > 1)
    [whiteCodes, whiteMap, kodBela] = huffman(white);
    [blackCodes, blackMap, kodCrna] = huffman(black);
    
  elseif (length(black) == 0 && length(white) == 1)
    kodCrna = [];
    kodBela = [white(1), 1];
    bs = 1;
    izhod = [0];
    
  elseif (length(white) == 1 && length(black) == 1)
    kodBela = [white(1), 1];
    bs = 1;
    kodCrna = [black(1), 1];
    izhod = [0, 0];
    
  endif
  
  if bs == 0
    result = [];
    j = 1;
    for i = (0:rowNum-1)
      sodo = 1;
      sum = 0;
      while (sum != colNum)
        if (mod(sodo,2) == 1)
          tmp = whiteMap(rowsVector(j)+1, :);
          code = tmp(end-tmp(1)+1:end);
        else
          tmp = blackMap(rowsVector(j)+1, :);
          code = tmp(end-tmp(1)+1:end);
        endif
        result = [result, code];
        sum += rowsVector(j);
        sodo += 1;
        j += 1;
      endwhile
    endfor
    izhod = result;
  endif
 
  R = numel(izhod)/numel(vhod);
  
endfunction

