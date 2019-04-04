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
  
  #function [huff] = huffwoman(huff, n)
  #  col = find(huff(:, 3) == 0);
  #  
  #  [freqSorted, indices] = sort(huff(:, 2)(col));
  #  
  #  newRow = [0, freqSorted(1) + freqSorted(2), 0, col(indices(1)), col(indices(2))];
  #  
  #  huff(col(indices(1)), 3) += 1;
  #  huff(col(indices(2)), 3) += 1;
  #  huff = [huff; newRow];
  #  
  # increase = nonzeros([huff(col(indices(1)), 4:5),huff(col(indices(2)), 4:5)]);
  #  if (any(increase))
  #    while (any(increase > n))
  #      temp = find(increase > n);
  #      increase = [increase;huff(increase(temp),4);huff(increase(temp),5)];
  #      increase(temp) = [];
  #    endwhile
  #    huff(increase, 3) += 1;
  #  endif
  #  
  #endfunction
  
  #function [huff] = huffwoman(huff, n)
  #  #sortedHuffTab = sortrows(huff,[1:2])
  # [colMin, row] = min(huff)
  #  firstMin= colMin(1);
  #  indexI=row(1);
  #  huff(indexI,1)=99;
  #  
  #  [colMin, row] = min(huff);
  #  secondMin = colMin(1);
  #  indexJ = row(1);
  #  huff(indexJ,1)=99;
  #  
  #  sum = firstMin + secondMin;
  #  
  #  sortedHuffTab(rows(huff)+1,1)=vsota;
  #  sortedHuffTab(rows(huff),3)=indexI;
  #  sortedHuffTab(rows(huff),4)=indexJ;
  #  
  #endfunction
    
    
  
  
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
    
    huff = [freq', uniq', zeros(length(freq),1,"float"), zeros(length(freq),1,"float"), zeros(length(freq),1,"float")];
    
    # TOLE
    #while (huff(end, 2) != 1)
    #  huff = huffwoman(huff, length(uniq));
    #endwhile
    
    #sortedHuffTab = sortrows(huffTab,[1:2]);
    sortedHuffTab = sortrows(huff,[1:2])
    
    sumMin = 0;
    while (sumMin < 0.9999999999)
      
      [colMin, row] = min(sortedHuffTab);
      firstMin= colMin(1);
      indexI=row(1);
      sortedHuffTab(indexI,1)=99;
      
      [colMin, row] = min(sortedHuffTab);
      secondMin = colMin(1);
      indexJ = row(1);
      sortedHuffTab(indexJ,1)=99;
      
      sumMin = firstMin + secondMin
      
      sortedHuffTab(rows(sortedHuffTab)+1,1)=sumMin;
      sortedHuffTab(rows(sortedHuffTab),3)=indexI;
      sortedHuffTab(rows(sortedHuffTab),4)=indexJ;
      
    endwhile
    
    zacVrstice = rows(huff)
    koncVrstice = rows(sortedHuffTab)
    
    while (koncVrstice > zacVrstice)
      
      indexI=sortedHuffTab(koncVrstice,3);
      indexJ=sortedHuffTab(koncVrstice,4);
      dolzinaVrstice = sortedHuffTab(koncVrstice,5);
      sortedHuffTab(indexI,5)+=(1+dolzinaVrstice);
      sortedHuffTab(indexJ,5)+=(1+dolzinaVrstice);
      sortedHuffTab(koncVrstice,5)=0;
      koncVrstice--;
      
    endwhile
    
    huff = sortedHuffTab
    
    codeLen = [huff(1:length(uniq), 2), huff(1:length(uniq), 5)];
    codeLen = sortrows(sortrows(codeLen, [1]),[2]);
    codes = [codeLen(:,2),encode(codeLen)];
    
    indices = codeLen(:, 1);
    map = zeros(colNum+1, max(codeLen(:, 2))+1);
    map(indices+1,:) = codes;
    
    
  endfunction
  
  
  [whiteCodes, whiteMap, kodBela] = huffman(white);
  [blackCodes, blackMap, kodCrna] = huffman(black);
  
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
  R = numel(izhod)/numel(vhod)
  
endfunction

