package Baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_3273_두수의합 {
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N+1];

		StringTokenizer st = new StringTokenizer(br.readLine());

		for(int i=1; i<N+1; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		int T = Integer.parseInt(br.readLine());// target number
		
		// 정렬
		Arrays.sort(A, 1, N+1);
		
		// 투 포인터
		
		int L = 1, R = N;
		int ans = 0;
		
		while(L<R) {
			if(A[L]+A[R] == T) {
				ans+=1;
				L++;
			}
			else if(A[L] + A[R] > T) R--;
			else L++;
		}
		
		System.out.println(ans);
	}
}
