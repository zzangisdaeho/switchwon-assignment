package com.switchwon.paymentimpl.user.in;

import com.switchwon.avro.request.ApprovalRequestDto;
import com.switchwon.avro.response.ApprovalResponseDto;
import com.switchwon.avro.response.BalanceResponseDto;
import com.switchwon.paymentimpl.user.service.ApproveService;
import com.switchwon.paymentimpl.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class UserController {

    private final UserService userService;
    private final ApproveService approveService;


    @GetMapping("/balance/{userId}")
    public BalanceResponseDto getBalance(@PathVariable String userId) {
        return userService.getUserInfo(userId);
    }

    @PostMapping("/approval")
    public ApprovalResponseDto buy(@RequestBody ApprovalRequestDto approvalRequestDto){
        return approveService.approveEventPublish(approvalRequestDto);
    }


}
