// prvi del, druga zanka
public static int prvi2 (int k, int m) {
	if (m <= 1) {
		return k;
	}
	return prvi2(k/16, m/2);
}

// prvi del, prva zanka
// i = 1
public static int prvi1 (int k, int m, int i) {
	if (i > m) {
		return k;
	}
	return prvi1(prvi2(k, m), m, i*2);
}

// drugi del, druga zanka
// j = 1
public static int drugi2 (int k, int m, int j) {
	if (j > m) {
		return k;
	}
	return drugi2(k*2, m, j+1);
}

// drugi del, prva zanka
public static int drugi1 (int k, int n, int m) {
	if (n <= 1) {
		return k;
	}
	return drugi1(drugi2(k, m, 1), n/2, m)
}