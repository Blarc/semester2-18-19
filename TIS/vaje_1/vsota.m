function V = vsota(A)
  V=0;
  for i=1:numel(A)
    V=V+A(i);
  endfor
endfunction