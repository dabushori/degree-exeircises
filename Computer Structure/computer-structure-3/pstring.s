    # 212945760 Ori Dabush

    .section .rodata
invalid_input: 
    .string "invalid input!\n"
a_ascii:
    .byte 97
A_ascii:
    .byte 65
z_ascii:
    .byte 122
Z_ascii:
    .byte 90
diff_ascii:
    .byte 32

    .text

    .globl pstrlen
    .globl replaceChar
    .globl pstrijcpy
    .globl swapCase
    .globl pstrijcmp

    .type pstrlen, @function
pstrlen:
    # %rdi - address of pstring 

    movzbq (%rdi), %rax
    ret 


    .type replaceChar, @function
replaceChar:
    # %rdi - address of pstring 
    # %rsi - old char
    # %rdx - new char

    movq %rdi, %r11     # save the pstring address
    
    pushq %rdi          # save all the caller registers before calling pstrlen
    pushq %rsi
    pushq %rdx

    call pstrlen        # Len is in %rax now
    
    popq %rdx
    popq %rsi
    popq %rdi           # get all the old value of the caller registers
        
    # loop through the string and replace the old char with the new
    cmpq $0, %rax       # index counter 
    je REPLACE_END
REPLACE_LOOP:
    incq %rdi           # move to the next char
    cmpb %sil, (%rdi)
    jne REPLACE_ELSE    # continue if not equal
    movb %dl, (%rdi)    # replace if equal
REPLACE_ELSE: 
    decq %rax           # decrement the loop counter by 1
    cmpq $0, %rax
    jne REPLACE_LOOP
REPLACE_END:
    movq %r11, %rax     # put the pstring address in %rax (return value)
    ret


    .type pstrijcpy, @function
pstrijcpy:
    # %rdi - address of dst pstring 
    # %rsi - address of src pstring
    # %rdx - char i 
    # %rcx - char j

    movq %rdi, %r8          # address of dst pstring now in %r8
    movq %rsi, %r9          # address of src pstring now in %r9

    pushq %rdx              # save all the caller registers before calling pstrlen
    pushq %rcx
    pushq %r8
    pushq %r9
    xorq %rax, %rax
    call pstrlen            # get pstrlen(dst)
    movq %rax, %r10         # pstrlen(dst) is in %r10 now
    popq %r9
    
    movq %r9, %rdi
    
    pushq %r9
    pushq %r10
    xorq %rax, %rax
    call pstrlen            # get pstrlen(src)
    movq %rax, %r11         # pstrlen(src) is in %r11 now
    popq %r10
    popq %r9
    popq %r8
    popq %rcx
    popq %rdx               # save all the caller registers before calling pstrlen

    cmp %rdx, %rcx          # assure that i <= j
    js COPY_INVALID_INPUT
    cmp %r10, %rcx          # assure that j < dst_len
    jns COPY_INVALID_INPUT
    cmp %r11, %rcx          # assure that j < src_len
    jns COPY_INVALID_INPUT
    cmpq $0, %rdx           # assure that 0 <= i
    js COPY_INVALID_INPUT

    leaq 1(%r9, %rdx), %r10 # get src[i] to %r10
    leaq 1(%r8, %rdx), %r11 # get dst[i] to %r11
    
    subq %rdx, %rcx
    addq $1, %rcx           # now j-i+1 is in %rcx (num of iterations)

    # loop through the string[i:j] and copy src to dst, %rcx is the loop counter
COPY_LOOP:
    movb (%r10), %al        # copy char from src to a temp register
    movb %al, (%r11)        # copy char from the temp register to dst
    incq %r10               # move to the next char of src
    incq %r11               # move to the next char of dst
    decq %rcx               # decrement the loop counter by 1
    cmpq $0, %rcx           # check if the counter reached 0
    je COPY_END
    jmp COPY_LOOP
    
COPY_INVALID_INPUT:
    movq $invalid_input, %rdi
    pushq %r8               # save the address of dst
    xorq %rax, %rax
    call printf             # print "Invalid input!\n"
    popq %r8
COPY_END:
    movq %r8, %rax          # put dst as a retrun value
    ret


    .type swapCase, @function
swapCase:
    # %rdi - address of pstring 

    movq %rdi, %r11         # save the pstring address
    
    pushq %rdi              # save all the caller registers before calling pstrlen

    call pstrlen            # Len is in %rax now
    
    popq %rdi               # get all the old value of the caller registers

    # loop through the string and swap the characters case
    cmpq $0, %rax # index counter 
    je SWAP_END
SWAP_LOOP:
    incq %rdi               # move to the next char
    movsbq A_ascii, %r10
    cmpb %r10b, (%rdi)      # cmp 'A', char (char - 'A')
    js SWAP_LOOP_INC
    movsbq Z_ascii, %r10
    cmpb (%rdi), %r10b      # cmp char, 'Z' ('Z' - char)
    jns TO_LOWER
    movsbq a_ascii, %r10
    cmpb %r10b, (%rdi)      # cmp 'a', char (char - 'a')
    js SWAP_LOOP_INC
    movsbq z_ascii, %r10
    cmpb (%rdi), %r10b      # cmp char, 'z' ('z' - char)
    js SWAP_LOOP_INC
    
    # to upper
    movsbq diff_ascii, %r10
    subb %r10b, (%rdi)
    jmp SWAP_LOOP_INC
TO_LOWER:
    movsbq diff_ascii, %r10
    addb %r10b, (%rdi)

SWAP_LOOP_INC: 
    decq %rax               # decrement the loop counter by 1
    cmpq $0, %rax
    jne SWAP_LOOP
SWAP_END:
    movq %r11, %rax         # put the pstring address in %rax (return value)
    ret


    .type pstrijcmp, @function
pstrijcmp:
    # %rdi - address of pstr1
    # %rsi - address of pstr2
    # %rdx - char i 
    # %rcx - char j

    movq %rdi, %r8          # address of pstr1 pstring now in %r8
    movq %rsi, %r9          # address of pstr2 pstring now in %r9

    pushq %rdx              # save all the caller registers before calling pstrlen
    pushq %rcx
    pushq %r8
    pushq %r9
    xorq %rax, %rax
    call pstrlen            # get pstrlen(pstr1)
    movq %rax, %r10         # pstrlen(pstr1) is in %r10 now

    movq %rsi, %rdi
    xorq %rax, %rax
    call pstrlen            # get pstrlen(pstr2)
    movq %rax, %r11         # pstrlen(pstr2) is in %r11 now
    popq %r9
    popq %r8
    popq %rcx
    popq %rdx               # save all the caller registers before calling pstrlen

    cmp %rdx, %rcx          # assure that i <= j
    js CMP_INVALID_INPUT
    cmp %r10, %rcx          # assure that j < pstr1_len
    jns CMP_INVALID_INPUT
    cmp %r11, %rcx          # assure that j < pstr2_len
    jns CMP_INVALID_INPUT
    cmpq $0, %rdx           # assure that 0 <= i
    js CMP_INVALID_INPUT

    leaq 1(%r9, %rdx), %r10 # get pstr2[i] to %r10
    leaq 1(%r8, %rdx), %r11 # get pstr1[i] to %r11
    
    subq %rdx, %rcx
    addq $1, %rcx           # now j-i+1 is in %rcx (num of iterations)

    # loop through the string[i:j] and copy pstr2 to pstr1, %rcx is the loop counter
CMP_LOOP:
    movb (%r10), %al        # put pstr2[curr] in %al
    cmpb %al, (%r11)        # cmpb pstr2[curr], pstr1[curr]
    js CMP_SMALLER
    cmpb (%r11), %al        # cmpb pstr1[curr], pstr2[curr]
    js CMP_BIGGER

    incq %r10               # move to the next char of pstr2
    incq %r11               # move to the next char of pstr1
    decq %rcx               # decrement the loop counter by 1
    cmp $0, %rcx            # check if the counter reached 0
    je CMP_END
    jmp CMP_LOOP
    
CMP_INVALID_INPUT:
    movq $invalid_input, %rdi
    xorq %rax, %rax
    call printf             # print "Invalid input!\n"
    movq $-2, %rax          # return value is -2 if there is an error
    ret
CMP_END:
    movq $0, %rax           # return value is 0 if they are equal
    ret
CMP_BIGGER:
    movq $1, %rax           # return value is 1 if pstr1 > pstr2 
    ret
CMP_SMALLER:
    movq $-1, %rax          # return value is -1 if pstr1 < pstr2 
    ret
