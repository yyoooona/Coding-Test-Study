package Programmers;

import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        
        HashMap<Integer, Integer> phoneketmonMap = new HashMap<>(); 
                
        for(int i=0; i<nums.length; i++){
            if(phoneketmonMap.get(nums[i]) != null)
            phoneketmonMap.put(nums[i], phoneketmonMap.get(nums[i]) + 1);
            else phoneketmonMap.put(nums[i], 1);
        }
        
        int result = nums.length/2;
        if(result>=phoneketmonMap.size()) answer = phoneketmonMap.size();
        else answer = result;
        // 위의 코드 다음과 같이 변경 가능
        // nums.length/2 > phoneketmonMap.size()?phoneketmonMap.size() : nums.length/2        
        
        return answer;
    }
}
