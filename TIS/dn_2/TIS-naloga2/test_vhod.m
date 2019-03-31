white = [];
black = [];
rows = [];

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
  
  rows = [rows, indRow];
  white = [white, whiteRow];
  black = [black, blackRow];
  
endfor