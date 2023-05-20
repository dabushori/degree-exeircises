    # 212945760 Ori Dabush

    .section .rodata
len_print:
    .string "first pstring length: %d, second pstring length: %d\n"
scan_2_chars:
    .string " %c %c"
print_52:
    .string "old char: %c, new char: %c, first string: %s, second string: %s\n"
print_string:
    .string "length: %d, string: %s\n"
invalid_option:
    .string "invalid option!\n"
scan_number:
    .string "%hhd"
compare_print:
    .string "compare result: %d\n"

    .align 8
SWITCH_ADDRESSES:
    .quad OPT50     # case 50
    .quad DEFAULT   # case 51
    .quad OPT52     # case 52
    .quad OPT53     # case 53
    .quad OPT54     # case 54
    .quad OPT55     # case 55
    .quad DEFAULT   # case 56
    .quad DEFAULT   # case 57
    .quad DEFAULT   # case 58
    .quad DEFAULT   # case 59
    .quad OPT50     # case 60

    .text

    .globl run_func

    .type run_func, @function
run_func:
    # %rdi - the option number
    # %rsi - address of pstr1
    # %rdx - address of pstr2

    leaq    -50(%rdi), %rdi                 # normalize the opt to 0 - 10 range
    cmpq    $10, %rdi                       # cmp 10, opt - 50
    ja      DEFAULT                         # if opt is not between 0 to 10, jump to default case
    jmp     *SWITCH_ADDRESSES(,%rdi, 8)     # otherwise, jump to the case of opt

OPT50:
    movq    %rsi, %rdi      # put pstr1 in %rdi as the first arg to pstrlen
    
    pushq   %rdx            # save pstr2
    xorq    %rax, %rax
    call    pstrlen
    popq    %rdx            # get pstr2 back
    movq    %rax, %rsi      # put pstrlen(pstr1) in %rsi as the second arg to printf
    
    movq    %rdx, %rdi      # put pstr2 in %rdi as the first arg to pstrlen
    xorq    %rax, %rax
    call    pstrlen
    movq    %rax, %rdx      # put pstrlen(pstr2) in %rdx as the third arg to printf

    movq    $len_print, %rdi
    xorq    %rax, %rax
    call    printf

    ret

OPT52:
    subq $8, %rsp               # allocate memory for the chars (it is 8 and not 2 because of alignment reasons)
    movq $scan_2_chars, %rdi    # format string
    movq %rsi, %rcx             # pstr1
    movq %rdx, %r8              # pstr2
    leaq (%rsp), %rsi           # address for the old char
    leaq 1(%rsp), %rdx          # address for the new char

    pushq %rcx                  # save all the caller registers
    pushq %r8
    pushq %rsi
    pushq %rdx
    xorq %rax, %rax             # clear %rax
    call scanf                  # get the chars
    popq %rdx
    popq %rsi
    popq %r8
    popq %rcx                   # get all the caller registers back

    movzbq (%rsi), %rsi         # the old char
    movzbq (%rdx), %rdx         # the new char

    addq $8, %rsp
    
    movq %rcx, %rdi         # put pstr1 address in %rdi as the first arg for rpelaceChar

    pushq %r8               # save all the caller registers
    pushq %rsi
    pushq %rdx
    call replaceChar        # call replaceChar for pstr2
    popq %rdx
    popq %rsi
    popq %r8                # get all the caller registers back

    movq %rax, %rcx         # save pstr1 address in %rcx (as 4th arg to printf)

    movq %r8, %rdi          # put pstr2 address in %rdi as the first arg for rpelaceChar

    pushq %rcx              # save all the caller registers
    pushq %rsi
    pushq %rdx
    call replaceChar        # call replaceChar for pstr2
    popq %rdx
    popq %rsi
    popq %rcx               # get all the caller registers back

    movq %rax, %r8          # save pstr2 address in %r8 (as 5th arg to printf)

    incq %rcx               # get the actual string (without length)
    incq %r8                # get the actual string (without length)

    movq $print_52, %rdi    # format string
    xorq %rax, %rax
    call printf

    ret

OPT53:
    pushq %rsi               # save the dst pstring
    pushq %rdx               # save the src pstring
    
    subq $8, %rsp

    movq $scan_number, %rdi
    movq %rsp, %rsi

    xorq %rax, %rax
    call scanf               # get i from the user

    movzbq (%rsp), %rdx      # i is in %rdx now

    movq $scan_number, %rdi
    movq %rsp, %rsi

    pushq %rdx
    pushq %rsi
    xorq %rax, %rax
    call scanf               # get j from the user
    popq %rsi
    popq %rdx

    movzbq (%rsi), %rcx      # j is in %rcx now

    addq $8, %rsp

    popq %rsi                # the src pstring is in %rsi now
    popq %rdi                # the dst pstring is in %rdi now

    pushq %rsi               # save the src string
    call pstrijcpy           # call the function
    popq %r8                 # get the src pstring back to %r8
    
    movq $print_string, %rdi # format string
    movzbq (%rax), %rsi
    leaq 1(%rax), %rdx

    pushq %r8                # save the second (src) pstring
    xorq %rax, %rax
    call printf              # print the first (dst) pstring
    popq %r8
    
    movq $print_string, %rdi # format string
    movzbq (%r8), %rsi
    leaq 1(%r8), %rdx

    xorq %rax, %rax
    call printf              # print the second (src) pstring

    ret

OPT54:
    movq %rsi, %rdi

    movq %rdx, %r8              # pstr2 is in %r8 now
    pushq %rdi                  # save the caller registers
    pushq %r8 
    call swapCase               # call swapCase for pstr1
    popq %r8
    popq %rdi                   # get the caller registers back

    movq $print_string, %rdi    # format string
    movzbq (%rax), %rsi         # put pstrlen(pstr1) in %rsi as a second arg for printf
    leaq 1(%rax), %rdx          # put pstr1 actual string in %rdx as a 3rd arg for printf

    pushq %r8                   # save pstr2 address
    xorq %rax, %rax
    call printf                 # print pstr1 info
    popq %r8                    # get pstr2 address back

    movq %r8, %rdi
    call swapCase               # call swapCase for pstr2
    
    movq $print_string, %rdi    # format string
    movzbq (%rax), %rsi         # put pstrlen(pstr2) in %rsi as a second arg for printf
    leaq 1(%rax), %rdx          # put pstr2 actual string in %rdx as a 3rd arg for printf

    xorq %rax, %rax
    call printf                 # print pstr2 info
    
    ret

OPT55:
    pushq %rsi                  # save pstr1
    pushq %rdx                  # save pstr2
    
    subq $8, %rsp

    movq $scan_number, %rdi
    movq %rsp, %rsi

    xorq %rax, %rax
    call scanf                  # get i from the user

    movzbq (%rsp), %rdx         # i is in %rdx now

    movq $scan_number, %rdi
    movq %rsp, %rsi

    pushq %rdx
    pushq %rsi
    xorq %rax, %rax
    call scanf                  # get j from the user
    popq %rsi
    popq %rdx

    movzbq (%rsi), %rcx         # j is in %rcx now 

    addq $8, %rsp

    popq %rsi                   # the pstr2 pstring is in %rsi now
    popq %rdi                   # the pstr1 pstring is in %rdi now
    
    pushq %rsi                  # save pstr2
    pushq %rdi                  # save pstr1
    call pstrijcmp              # call the function
    popq %rsi                   # get pstr1 pstring back to %rsi
    popq %r8                    # get pstr2 pstring back to %r8

    movq $compare_print, %rdi
    movq %rax, %rsi

    xorq %rax, %rax
    call printf                 # print the compare result

    ret

DEFAULT:
    movq $invalid_option, %rdi
    xorq %rax, %rax
    call printf                 # print "Invalid option!\n" message
    ret
